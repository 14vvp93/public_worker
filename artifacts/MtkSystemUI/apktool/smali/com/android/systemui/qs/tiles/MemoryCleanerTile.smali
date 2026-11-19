.class public Lcom/android/systemui/qs/tiles/MemoryCleanerTile;
.super Lcom/android/systemui/qs/tileimpl/QSTileImpl;
.source "MemoryCleanerTile.java"


# annotations
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


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/QSHost;)V
    .locals 1
    .param p1, "host"    # Lcom/android/systemui/qs/QSHost;

    .line 56
    invoke-direct {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;-><init>(Lcom/android/systemui/qs/QSHost;)V

    .line 97
    new-instance v0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;

    invoke-direct {v0, p0}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;-><init>(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)V

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mHandler:Landroid/os/Handler;

    .line 57
    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    .line 50
    invoke-direct {p0}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->clearRecentsTask()V

    return-void
.end method

.method static synthetic access$100(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    .line 50
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    .line 50
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method private clearRecentsTask()V
    .locals 13

    .line 112
    invoke-static {}, Lcom/android/systemui/recents/Recents;->getTaskLoader()Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;

    move-result-object v0

    .line 113
    .local v0, "loader":Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;
    new-instance v1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;

    iget-object v2, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;-><init>(Landroid/content/Context;)V

    .line 114
    .local v1, "loadPlan":Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;
    const/4 v2, -0x1

    invoke-virtual {v0, v1, v2}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->preloadTasks(Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;I)V

    .line 117
    invoke-static {}, Lcom/android/systemui/recents/Recents;->getConfiguration()Lcom/android/systemui/recents/RecentsConfiguration;

    move-result-object v2

    .line 118
    .local v2, "config":Lcom/android/systemui/recents/RecentsConfiguration;
    invoke-virtual {v2}, Lcom/android/systemui/recents/RecentsConfiguration;->getLaunchState()Lcom/android/systemui/recents/RecentsActivityLaunchState;

    move-result-object v3

    .line 120
    .local v3, "launchState":Lcom/android/systemui/recents/RecentsActivityLaunchState;
    new-instance v4, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;

    invoke-direct {v4}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;-><init>()V

    .line 121
    .local v4, "loadOpts":Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;
    iget v5, v3, Lcom/android/systemui/recents/RecentsActivityLaunchState;->launchedNumVisibleTasks:I

    iput v5, v4, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->numVisibleTasks:I

    .line 122
    iget v5, v3, Lcom/android/systemui/recents/RecentsActivityLaunchState;->launchedNumVisibleThumbnails:I

    iput v5, v4, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->numVisibleTaskThumbnails:I

    .line 123
    invoke-virtual {v0, v1, v4}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->loadTasks(Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;)V

    .line 124
    invoke-virtual {v1}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->getTaskStack()Lcom/android/systemui/shared/recents/model/TaskStack;

    move-result-object v5

    .line 126
    .local v5, "stack":Lcom/android/systemui/shared/recents/model/TaskStack;
    invoke-virtual {v5}, Lcom/android/systemui/shared/recents/model/TaskStack;->getTasks()Ljava/util/ArrayList;

    move-result-object v6

    .line 127
    .local v6, "tasks":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/android/systemui/shared/recents/model/Task;>;"
    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v7

    .line 128
    .local v7, "taskCount":I
    const/4 v8, 0x0

    move v9, v8

    .local v9, "i":I
    :goto_0
    if-ge v9, v7, :cond_0

    .line 129
    invoke-virtual {v6, v9}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Lcom/android/systemui/shared/recents/model/Task;

    .line 133
    .local v10, "task":Lcom/android/systemui/shared/recents/model/Task;
    invoke-virtual {v0, v10, v8}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->deleteTaskData(Lcom/android/systemui/shared/recents/model/Task;Z)V

    .line 136
    invoke-static {}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    move-result-object v11

    iget-object v12, v10, Lcom/android/systemui/shared/recents/model/Task;->key:Lcom/android/systemui/shared/recents/model/Task$TaskKey;

    iget v12, v12, Lcom/android/systemui/shared/recents/model/Task$TaskKey;->id:I

    invoke-virtual {v11, v12}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->removeTask(I)V

    .line 138
    iget-object v11, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    const-string v12, "activity"

    invoke-virtual {v11, v12}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Landroid/app/ActivityManager;

    .line 139
    .local v11, "am":Landroid/app/ActivityManager;
    iget-object v12, v10, Lcom/android/systemui/shared/recents/model/Task;->key:Lcom/android/systemui/shared/recents/model/Task$TaskKey;

    invoke-virtual {v12}, Lcom/android/systemui/shared/recents/model/Task$TaskKey;->getComponent()Landroid/content/ComponentName;

    move-result-object v12

    invoke-virtual {v12}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Landroid/app/ActivityManager;->forceStopPackage(Ljava/lang/String;)V

    .line 128
    .end local v10    # "task":Lcom/android/systemui/shared/recents/model/Task;
    .end local v11    # "am":Landroid/app/ActivityManager;
    add-int/lit8 v9, v9, 0x1

    goto :goto_0

    .line 141
    .end local v9    # "i":I
    :cond_0
    return-void
.end method


# virtual methods
.method public getLongClickIntent()Landroid/content/Intent;
    .locals 1

    .line 70
    const/4 v0, 0x0

    return-object v0
.end method

.method public getMetricsCategory()I
    .locals 1

    .line 94
    const/16 v0, 0x7a

    return v0
.end method

.method public getTileLabel()Ljava/lang/CharSequence;
    .locals 2

    .line 82
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104a0

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected handleClick()V
    .locals 4

    .line 75
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mHost:Lcom/android/systemui/qs/QSHost;

    invoke-interface {v0}, Lcom/android/systemui/qs/QSHost;->collapsePanels()V

    .line 76
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v0

    .line 77
    .local v0, "msg":Landroid/os/Message;
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mHandler:Landroid/os/Handler;

    const-wide/16 v2, 0x5dc

    invoke-virtual {v1, v0, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 78
    return-void
.end method

.method public handleSetListening(Z)V
    .locals 0
    .param p1, "listening"    # Z

    .line 66
    return-void
.end method

.method protected handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V
    .locals 2
    .param p1, "state"    # Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .param p2, "arg"    # Ljava/lang/Object;

    .line 87
    const/4 v0, 0x1

    iput-boolean v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->value:Z

    .line 88
    const v0, 0x7f0802a2

    invoke-static {v0}, Lcom/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon;->get(I)Lcom/android/systemui/plugins/qs/QSTile$Icon;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->icon:Lcom/android/systemui/plugins/qs/QSTile$Icon;

    .line 89
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->mContext:Landroid/content/Context;

    const v1, 0x7f1104a0

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;->label:Ljava/lang/CharSequence;

    .line 90
    return-void
.end method

.method protected bridge synthetic handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$State;Ljava/lang/Object;)V
    .locals 0

    .line 50
    check-cast p1, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->handleUpdateState(Lcom/android/systemui/plugins/qs/QSTile$BooleanState;Ljava/lang/Object;)V

    return-void
.end method

.method public newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;
    .locals 1

    .line 61
    new-instance v0, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/QSTile$BooleanState;-><init>()V

    return-object v0
.end method

.method public bridge synthetic newTileState()Lcom/android/systemui/plugins/qs/QSTile$State;
    .locals 1

    .line 50
    invoke-virtual {p0}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->newTileState()Lcom/android/systemui/plugins/qs/QSTile$BooleanState;

    move-result-object v0

    return-object v0
.end method
