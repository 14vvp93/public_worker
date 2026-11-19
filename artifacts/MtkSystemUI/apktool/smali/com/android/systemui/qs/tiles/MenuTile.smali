.class public Lcom/android/systemui/qs/tiles/MenuTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "MenuTile.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;,
        Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/systemui/qs/tileimpl/QSTileImpl<",
        "Lcom/android/systemui/plugins/qs/QSTile$BooleanState;",
        ">;"
    }
.end annotation


# instance fields
.field mHandler:Landroid/os/Handler;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "HandlerLeak"
        }
    .end annotation
.end field

.field private mMenuListAdapter:Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 1
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 53
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 94
    new-instance v0, Lcom/android/systemui/qs/tiles/MenuTile$1;

    invoke-direct {v0, p0}, Lcom/android/systemui/qs/tiles/MenuTile$1;-><init>(Lcom/android/systemui/qs/tiles/MenuTile;)V

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mHandler:Landroid/os/Handler;

    .line 54
    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/qs/tiles/MenuTile;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/MenuTile;

    .line 46
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/MenuTile;->launchMenuTask()V

    return-void
.end method

.method public static synthetic lambda$launchMenuTask$0(Lcom/android/systemui/qs/tiles/MenuTile;Ljava/util/ArrayList;Lcom/android/systemui/statusbar/phone/SystemUIDialog;Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 2
    .param p1, "packageNameList"    # Ljava/util/ArrayList;
    .param p2, "dialog"    # Lcom/android/systemui/statusbar/phone/SystemUIDialog;
    .param p3, "parent"    # Landroid/widget/AdapterView;
    .param p4, "view"    # Landroid/view/View;
    .param p5, "position"    # I
    .param p6, "id"    # J

    .line 123
    invoke-virtual {p1, p5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 124
    .local v0, "packageName":Ljava/lang/String;
    const/4 v1, 0x1

    if-ne v1, p5, :cond_0

    .line 125
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/MenuTile;->openAppManagement()V

    goto :goto_0

    .line 127
    :cond_0
    invoke-direct {p0, v0}, Lcom/android/systemui/qs/tiles/MenuTile;->launchApp(Ljava/lang/String;)V

    .line 129
    :goto_0
    invoke-virtual {p2}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;->dismiss()V

    .line 130
    return-void
.end method

.method private launchApp(Ljava/lang/String;)V
    .locals 5
    .param p1, "packageName"    # Ljava/lang/String;

    .line 149
    const/4 v0, 0x1

    const v1, 0x7f1104a3

    :try_start_0
    iget-object v2, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/pm/PackageManager;->getLaunchIntentForPackage(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v2

    .line 150
    .local v2, "intent":Landroid/content/Intent;
    if-eqz v2, :cond_0

    .line 151
    const v3, 0x10008000

    invoke-virtual {v2, v3}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 152
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-virtual {v3, v2}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    goto :goto_0

    .line 154
    :cond_0
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    iget-object v4, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-virtual {v4, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v3

    invoke-virtual {v3}, Landroid/widget/Toast;->show()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 158
    .end local v2    # "intent":Landroid/content/Intent;
    :goto_0
    goto :goto_1

    .line 156
    :catch_0
    move-exception v2

    .line 157
    .local v2, "e":Ljava/lang/Exception;
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    iget-object v4, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-virtual {v4, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v3, v1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 159
    .end local v2    # "e":Ljava/lang/Exception;
    :goto_1
    return-void
.end method

.method private launchMenuTask()V
    .locals 9

    .line 106
    new-instance v0, Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const/4 v2, 0x5

    invoke-direct {v0, v1, v2}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;-><init>(Landroid/content/Context;I)V

    .line 107
    .local v0, "dialog":Lcom/android/systemui/statusbar/phone/SystemUIDialog;
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;->getWindow()Landroid/view/Window;

    move-result-object v1

    const v2, 0x106000d

    invoke-virtual {v1, v2}, Landroid/view/Window;->setBackgroundDrawableResource(I)V

    .line 108
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v2, 0x7f0d0176

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v1

    .line 110
    .local v1, "contentView":Landroid/view/View;
    const v2, 0x7f0a026a

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ListView;

    .line 114
    .local v2, "listView":Landroid/widget/ListView;
    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v4, 0x7f1104a4

    invoke-virtual {v3, v4}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v3

    .line 115
    .local v3, "namesString":Ljava/lang/String;
    iget-object v4, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v5, 0x7f1104a5

    invoke-virtual {v4, v5}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v4

    .line 116
    .local v4, "packagesString":Ljava/lang/String;
    new-instance v5, Ljava/util/ArrayList;

    const-string v6, ","

    invoke-virtual {v3, v6}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    invoke-static {v6}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    .line 117
    .local v5, "menuNameList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/String;>;"
    new-instance v6, Ljava/util/ArrayList;

    const-string v7, ","

    invoke-virtual {v4, v7}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v7

    invoke-direct {v6, v7}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    .line 119
    .local v6, "packageNameList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/String;>;"
    new-instance v7, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;

    iget-object v8, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-direct {v7, p0, v8, v5, v6}, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;-><init>(Lcom/android/systemui/qs/tiles/MenuTile;Landroid/content/Context;Ljava/util/ArrayList;Ljava/util/ArrayList;)V

    iput-object v7, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mMenuListAdapter:Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;

    .line 120
    iget-object v7, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mMenuListAdapter:Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;

    invoke-virtual {v2, v7}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 122
    new-instance v7, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;

    invoke-direct {v7, p0, v6, v0}, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;-><init>(Lcom/android/systemui/qs/tiles/MenuTile;Ljava/util/ArrayList;Lcom/android/systemui/statusbar/phone/SystemUIDialog;)V

    invoke-virtual {v2, v7}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 132
    invoke-virtual {v0, v1}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;->setView(Landroid/view/View;)V

    .line 133
    const/4 v7, 0x1

    invoke-virtual {v0, v7}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;->setCancelable(Z)V

    .line 134
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/SystemUIDialog;->show()V

    .line 135
    return-void
.end method

.method private openAppManagement()V
    .locals 4

    .line 139
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.settings.MANAGE_APPLICATIONS_SETTINGS"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 140
    .local v0, "intent":Landroid/content/Intent;
    const v1, 0x10008000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 141
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 144
    .end local v0    # "intent":Landroid/content/Intent;
    goto :goto_0

    .line 142
    :catch_0
    move-exception v0

    .line 143
    .local v0, "e":Ljava/lang/Exception;
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v3, 0x7f1104a3

    invoke-virtual {v2, v3}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x1

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 145
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 67
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 91
    const/16 v0, 0x7a

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 79
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104a2

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected handleClick()V
    .locals 4

    .line 72
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mHost:Lcom/android/systemui/qs/QSHost;

    invoke-interface {v0}, Lcom/android/systemui/qs/QSHost;->collapsePanels()V

    .line 73
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v0

    .line 74
    .local v0, "msg":Landroid/os/Message;
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mHandler:Landroid/os/Handler;

    const-wide/16 v2, 0x5dc

    invoke-virtual {v1, v0, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 75
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 0
    .param p1, "listening"    # Z

    .line 63
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V
    .locals 2
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 84
    const/4 v0, 0x1

    iput-boolean v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->value:Z

    .line 85
    const v0, 0x7f080438

    invoke-static {v0}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 86
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104a2

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 87
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 46
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/qs/tiles/MenuTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .locals 1

    .line 58
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 46
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/MenuTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    move-result-object v0

    return-object v0
.end method
