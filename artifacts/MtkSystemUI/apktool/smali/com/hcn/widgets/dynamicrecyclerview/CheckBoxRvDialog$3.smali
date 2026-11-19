.class Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;
.super Ljava/lang/Object;
.source "CheckBoxRvDialog.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->processData()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 523
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 8

    .line 526
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$400(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Landroid/content/Context;

    move-result-object v0

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/Utils;->getInstalledApps(Landroid/content/Context;Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v0

    .line 527
    .local v0, "installedApps":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/content/pm/LauncherActivityInfo;>;"
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    new-instance v3, Lcom/google/gson/Gson;

    invoke-direct {v3}, Lcom/google/gson/Gson;-><init>()V

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$700(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;

    move-result-object v4

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$600(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5, v1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPUtils;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    new-instance v4, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3$1;

    invoke-direct {v4, p0}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3$1;-><init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;)V

    invoke-virtual {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3$1;->getType()Ljava/lang/reflect/Type;

    move-result-object v4

    invoke-virtual {v3, v1, v4}, Lcom/google/gson/Gson;->fromJson(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/ArrayList;

    invoke-static {v2, v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$502(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;

    .line 530
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v1

    const/4 v2, 0x0

    if-nez v1, :cond_1

    .line 531
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;

    move-result-object v1

    array-length v1, v1

    if-lez v1, :cond_1

    .line 532
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    invoke-static {v1, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$502(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;

    .line 533
    move v1, v2

    .local v1, "i":I
    :goto_0
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;

    move-result-object v3

    array-length v3, v3

    if-ge v1, v3, :cond_1

    .line 534
    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;

    move-result-object v3

    aget-object v3, v3, v1

    .line 535
    .local v3, "key":Ljava/lang/String;
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v4, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mapToComponentString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 536
    .local v4, "value":Ljava/lang/String;
    invoke-static {}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$000()Ljava/lang/String;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "key = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v7, ", value = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 537
    if-eqz v4, :cond_0

    .line 538
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v5

    iget-object v6, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    iget-object v7, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v7}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$800(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)[Ljava/lang/String;

    move-result-object v7

    aget-object v7, v7, v1

    invoke-virtual {v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->mapToComponentString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 533
    .end local v3    # "key":Ljava/lang/String;
    .end local v4    # "value":Ljava/lang/String;
    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 543
    .end local v1    # "i":I
    :cond_1
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    invoke-static {v1, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$902(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;

    .line 544
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    invoke-static {v1, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1002(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;Ljava/util/ArrayList;)Ljava/util/ArrayList;

    .line 546
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_4

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/content/pm/LauncherActivityInfo;

    .line 547
    .local v3, "appInfo":Landroid/content/pm/LauncherActivityInfo;
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v4, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->accept(Landroid/content/pm/LauncherActivityInfo;)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 548
    new-instance v4, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {v3}, Landroid/content/pm/LauncherActivityInfo;->getLabel()Ljava/lang/CharSequence;

    move-result-object v5

    invoke-interface {v5}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v3}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v6

    iget-object v7, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v7, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    invoke-direct {v4, v5, v6, v7}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;-><init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V

    .line 549
    .local v4, "tmpInfo":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v5

    if-eqz v5, :cond_2

    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v5

    invoke-virtual {v3}, Landroid/content/pm/LauncherActivityInfo;->getComponentName()Landroid/content/ComponentName;

    move-result-object v6

    invoke-virtual {v6}, Landroid/content/ComponentName;->flattenToString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    .line 550
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v5

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_2

    .line 552
    :cond_2
    iget-object v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v5}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v5

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 555
    .end local v3    # "appInfo":Landroid/content/pm/LauncherActivityInfo;
    .end local v4    # "tmpInfo":Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    :cond_3
    :goto_2
    goto :goto_1

    .line 556
    :cond_4
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1100(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v1

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v3

    invoke-virtual {v1, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->setList(Ljava/util/List;)V

    .line 557
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1200(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;

    move-result-object v1

    iget-object v3, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v3

    invoke-virtual {v1, v3}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$CheckBoxAdapter;->setList(Ljava/util/List;)V

    .line 558
    iget-object v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-virtual {v1}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->updateSelectRVLayoutParams()V

    .line 560
    invoke-static {}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$000()Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "installedApps size = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 561
    invoke-static {}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$000()Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "mUnselectedApps size = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$1000(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 562
    invoke-static {}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$000()Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "mSelectedSaveApps size = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v4

    if-nez v4, :cond_5

    move v4, v2

    goto :goto_3

    :cond_5
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$500(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    :goto_3
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 563
    invoke-static {}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$000()Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "mSelectedApps size = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v4}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v4

    if-nez v4, :cond_6

    goto :goto_4

    :cond_6
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$3;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-static {v2}, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->access$900(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    :goto_4
    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 564
    return-void
.end method
