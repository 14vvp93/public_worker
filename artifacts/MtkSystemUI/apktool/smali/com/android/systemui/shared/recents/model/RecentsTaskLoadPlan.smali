.class public Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;
.super Ljava/lang/Object;
.source "RecentsTaskLoadPlan.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;,
        Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$PreloadOptions;
    }
.end annotation


# instance fields
.field private final mContext:Landroid/content/Context;

.field private final mKeyguardManager:Landroid/app/KeyguardManager;

.field private mRawTasks:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/app/ActivityManager$RecentTaskInfo;",
            ">;"
        }
    .end annotation
.end field

.field private mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

.field private final mTmpLockedUsers:Landroid/util/SparseBooleanArray;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 76
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 74
    new-instance v0, Landroid/util/SparseBooleanArray;

    invoke-direct {v0}, Landroid/util/SparseBooleanArray;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mTmpLockedUsers:Landroid/util/SparseBooleanArray;

    .line 77
    iput-object p1, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mContext:Landroid/content/Context;

    .line 78
    const-string v0, "keyguard"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/KeyguardManager;

    iput-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mKeyguardManager:Landroid/app/KeyguardManager;

    .line 79
    return-void
.end method


# virtual methods
.method public executePlan(Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;)V
    .locals 12
    .param p1, "opts"    # Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;
    .param p2, "loader"    # Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;

    .line 194
    iget-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 197
    .local v0, "res":Landroid/content/res/Resources;
    iget-object v1, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    invoke-virtual {v1}, Lcom/android/systemui/shared/recents/model/TaskStack;->getTasks()Ljava/util/ArrayList;

    move-result-object v1

    .line 198
    .local v1, "tasks":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/android/systemui/shared/recents/model/Task;>;"
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v2

    .line 199
    .local v2, "taskCount":I
    const/4 v3, 0x0

    move v4, v3

    .local v4, "i":I
    :goto_0
    if-ge v4, v2, :cond_7

    .line 200
    invoke-virtual {v1, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/android/systemui/shared/recents/model/Task;

    .line 201
    .local v5, "task":Lcom/android/systemui/shared/recents/model/Task;
    iget-object v6, v5, Lcom/android/systemui/shared/recents/model/Task;->key:Lcom/android/systemui/shared/recents/model/Task$TaskKey;

    .line 203
    .local v6, "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    iget-object v7, v5, Lcom/android/systemui/shared/recents/model/Task;->key:Lcom/android/systemui/shared/recents/model/Task$TaskKey;

    iget v7, v7, Lcom/android/systemui/shared/recents/model/Task$TaskKey;->id:I

    iget v8, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->runningTaskId:I

    const/4 v9, 0x1

    if-ne v7, v8, :cond_0

    move v7, v9

    goto :goto_1

    :cond_0
    move v7, v3

    .line 204
    .local v7, "isRunningTask":Z
    :goto_1
    iget v8, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->numVisibleTasks:I

    sub-int v8, v2, v8

    if-lt v4, v8, :cond_1

    move v8, v9

    goto :goto_2

    :cond_1
    move v8, v3

    .line 205
    .local v8, "isVisibleTask":Z
    :goto_2
    iget v10, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->numVisibleTaskThumbnails:I

    sub-int v10, v2, v10

    if-lt v4, v10, :cond_2

    move v10, v9

    goto :goto_3

    :cond_2
    move v10, v3

    .line 208
    .local v10, "isVisibleThumbnail":Z
    :goto_3
    iget-boolean v11, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->onlyLoadPausedActivities:Z

    if-eqz v11, :cond_3

    if-eqz v7, :cond_3

    .line 209
    goto :goto_4

    .line 212
    :cond_3
    iget-boolean v11, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->loadIcons:Z

    if-eqz v11, :cond_5

    if-nez v7, :cond_4

    if-eqz v8, :cond_5

    .line 213
    :cond_4
    iget-object v11, v5, Lcom/android/systemui/shared/recents/model/Task;->icon:Landroid/graphics/drawable/Drawable;

    if-nez v11, :cond_5

    .line 214
    iget-object v11, v5, Lcom/android/systemui/shared/recents/model/Task;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    invoke-virtual {p2, v6, v11, v9}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateActivityIcon(Lcom/android/systemui/shared/recents/model/Task$TaskKey;Landroid/app/ActivityManager$TaskDescription;Z)Landroid/graphics/drawable/Drawable;

    move-result-object v11

    iput-object v11, v5, Lcom/android/systemui/shared/recents/model/Task;->icon:Landroid/graphics/drawable/Drawable;

    .line 218
    :cond_5
    iget-boolean v11, p1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$Options;->loadThumbnails:Z

    if-eqz v11, :cond_6

    if-eqz v10, :cond_6

    .line 219
    invoke-virtual {p2, v6, v9, v9}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateThumbnail(Lcom/android/systemui/shared/recents/model/Task$TaskKey;ZZ)Lcom/android/systemui/shared/recents/model/ThumbnailData;

    move-result-object v9

    iput-object v9, v5, Lcom/android/systemui/shared/recents/model/Task;->thumbnail:Lcom/android/systemui/shared/recents/model/ThumbnailData;

    .line 199
    .end local v5    # "task":Lcom/android/systemui/shared/recents/model/Task;
    .end local v6    # "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    .end local v7    # "isRunningTask":Z
    .end local v8    # "isVisibleTask":Z
    .end local v10    # "isVisibleThumbnail":Z
    :cond_6
    :goto_4
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 223
    .end local v4    # "i":I
    :cond_7
    return-void
.end method

.method public getTaskStack()Lcom/android/systemui/shared/recents/model/TaskStack;
    .locals 1

    .line 229
    iget-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    return-object v0
.end method

.method public hasTasks()Z
    .locals 2

    .line 234
    iget-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 235
    iget-object v0, p0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    invoke-virtual {v0}, Lcom/android/systemui/shared/recents/model/TaskStack;->getTaskCount()I

    move-result v0

    if-lez v0, :cond_0

    const/4 v1, 0x1

    nop

    :cond_0
    return v1

    .line 237
    :cond_1
    return v1
.end method

.method public preloadPlan(Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$PreloadOptions;Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;II)V
    .locals 46
    .param p1, "opts"    # Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$PreloadOptions;
    .param p2, "loader"    # Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;
    .param p3, "runningTaskId"    # I
    .param p4, "currentUserId"    # I

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move-object/from16 v2, p2

    .line 94
    iget-object v3, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    .line 95
    .local v3, "res":Landroid/content/res/Resources;
    sget v4, Lcom/android/systemui/shared/R$array;->recents_task_hidden_list:I

    invoke-virtual {v3, v4}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    .line 97
    .local v4, "hiddenList":Ljava/util/List;
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    .line 98
    .local v5, "allTasks":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/android/systemui/shared/recents/model/Task;>;"
    iget-object v6, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mRawTasks:Ljava/util/List;

    if-nez v6, :cond_0

    .line 99
    invoke-static {}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getInstance()Lcom/android/systemui/shared/system/ActivityManagerWrapper;

    move-result-object v6

    .line 100
    invoke-static {}, Landroid/app/ActivityManager;->getMaxRecentTasksStatic()I

    move-result v7

    .line 99
    move/from16 v8, p4

    invoke-virtual {v6, v7, v8}, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->getRecentTasks(II)Ljava/util/List;

    move-result-object v6

    iput-object v6, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mRawTasks:Ljava/util/List;

    .line 103
    iget-object v6, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mRawTasks:Ljava/util/List;

    invoke-static {v6}, Ljava/util/Collections;->reverse(Ljava/util/List;)V

    goto :goto_0

    .line 106
    :cond_0
    move/from16 v8, p4

    :goto_0
    iget-object v6, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mRawTasks:Ljava/util/List;

    invoke-interface {v6}, Ljava/util/List;->size()I

    move-result v6

    .line 107
    .local v6, "taskCount":I
    const/4 v9, 0x0

    .local v9, "i":I
    :goto_1
    if-ge v9, v6, :cond_10

    .line 108
    iget-object v10, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mRawTasks:Ljava/util/List;

    invoke-interface {v10, v9}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Landroid/app/ActivityManager$RecentTaskInfo;

    .line 111
    .local v10, "t":Landroid/app/ActivityManager$RecentTaskInfo;
    iget-object v11, v10, Landroid/app/ActivityManager$RecentTaskInfo;->origActivity:Landroid/content/ComponentName;

    if-eqz v11, :cond_1

    .line 113
    iget-object v11, v10, Landroid/app/ActivityManager$RecentTaskInfo;->origActivity:Landroid/content/ComponentName;

    goto :goto_2

    .line 115
    :cond_1
    iget-object v11, v10, Landroid/app/ActivityManager$RecentTaskInfo;->realActivity:Landroid/content/ComponentName;

    .line 116
    .local v11, "sourceComponent":Landroid/content/ComponentName;
    :goto_2
    iget-object v12, v10, Landroid/app/ActivityManager$RecentTaskInfo;->configuration:Landroid/content/res/Configuration;

    iget-object v12, v12, Landroid/content/res/Configuration;->windowConfiguration:Landroid/app/WindowConfiguration;

    invoke-virtual {v12}, Landroid/app/WindowConfiguration;->getWindowingMode()I

    move-result v15

    .line 117
    .local v15, "windowingMode":I
    new-instance v20, Lcom/android/systemui/shared/recents/model/Task$TaskKey;

    iget v13, v10, Landroid/app/ActivityManager$RecentTaskInfo;->persistentId:I

    iget-object v14, v10, Landroid/app/ActivityManager$RecentTaskInfo;->baseIntent:Landroid/content/Intent;

    iget v12, v10, Landroid/app/ActivityManager$RecentTaskInfo;->userId:I

    iget-wide v7, v10, Landroid/app/ActivityManager$RecentTaskInfo;->lastActiveTime:J

    move/from16 v17, v12

    move-object/from16 v12, v20

    move-object/from16 v16, v14

    move v14, v15

    move-object/from16 v21, v3

    move v3, v15

    move-object/from16 v15, v16

    .end local v15    # "windowingMode":I
    .local v3, "windowingMode":I
    .local v21, "res":Landroid/content/res/Resources;
    move-object/from16 v16, v11

    move-wide/from16 v18, v7

    invoke-direct/range {v12 .. v19}, Lcom/android/systemui/shared/recents/model/Task$TaskKey;-><init>(IILandroid/content/Intent;Landroid/content/ComponentName;IJ)V

    move-object/from16 v7, v20

    .line 120
    .local v7, "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    const/4 v8, 0x5

    if-ne v3, v8, :cond_2

    const/4 v8, 0x1

    goto :goto_3

    :cond_2
    const/4 v8, 0x0

    .line 121
    .local v8, "isFreeformTask":Z
    :goto_3
    if-nez v8, :cond_3

    const/4 v13, 0x1

    goto :goto_4

    :cond_3
    const/4 v13, 0x0

    .line 122
    .local v13, "isStackTask":Z
    :goto_4
    iget v14, v7, Lcom/android/systemui/shared/recents/model/Task$TaskKey;->id:I

    move/from16 v15, p3

    if-ne v14, v15, :cond_4

    const/16 v30, 0x1

    goto :goto_5

    :cond_4
    const/16 v30, 0x0

    .line 124
    .local v30, "isLaunchTarget":Z
    :goto_5
    invoke-virtual {v2, v7}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateActivityInfo(Lcom/android/systemui/shared/recents/model/Task$TaskKey;)Landroid/content/pm/ActivityInfo;

    move-result-object v14

    .line 125
    .local v14, "info":Landroid/content/pm/ActivityInfo;
    if-nez v14, :cond_5

    .line 126
    nop

    .line 107
    move-object/from16 v41, v4

    move/from16 v40, v6

    goto/16 :goto_10

    .line 130
    :cond_5
    const/16 v16, 0x0

    .line 131
    .local v16, "alreadyExist":Z
    const/16 v17, 0x0

    .local v17, "j":I
    :goto_6
    move/from16 v38, v17

    .end local v17    # "j":I
    .local v38, "j":I
    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v12

    move/from16 v39, v3

    move/from16 v3, v38

    if-ge v3, v12, :cond_7

    .line 132
    .end local v38    # "j":I
    .local v3, "j":I
    .local v39, "windowingMode":I
    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Lcom/android/systemui/shared/recents/model/Task;

    .line 133
    .local v12, "task":Lcom/android/systemui/shared/recents/model/Task;
    move/from16 v40, v6

    invoke-virtual {v12}, Lcom/android/systemui/shared/recents/model/Task;->getTopComponent()Landroid/content/ComponentName;

    move-result-object v6

    .end local v6    # "taskCount":I
    .local v40, "taskCount":I
    invoke-virtual {v6, v11}, Landroid/content/ComponentName;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_6

    .line 134
    const/16 v16, 0x1

    .line 135
    goto :goto_7

    .line 131
    .end local v12    # "task":Lcom/android/systemui/shared/recents/model/Task;
    :cond_6
    add-int/lit8 v17, v3, 0x1

    .end local v3    # "j":I
    .restart local v17    # "j":I
    move/from16 v3, v39

    move/from16 v6, v40

    goto :goto_6

    .line 138
    .end local v17    # "j":I
    .end local v40    # "taskCount":I
    .restart local v6    # "taskCount":I
    :cond_7
    move/from16 v40, v6

    .end local v6    # "taskCount":I
    .restart local v40    # "taskCount":I
    :goto_7
    if-eqz v16, :cond_8

    .line 139
    nop

    .line 107
    .end local v4    # "hiddenList":Ljava/util/List;
    .end local v7    # "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    .end local v8    # "isFreeformTask":Z
    .end local v10    # "t":Landroid/app/ActivityManager$RecentTaskInfo;
    .end local v11    # "sourceComponent":Landroid/content/ComponentName;
    .end local v13    # "isStackTask":Z
    .end local v14    # "info":Landroid/content/pm/ActivityInfo;
    .end local v16    # "alreadyExist":Z
    .end local v30    # "isLaunchTarget":Z
    .end local v39    # "windowingMode":I
    .local v41, "hiddenList":Ljava/util/List;
    :goto_8
    move-object/from16 v41, v4

    goto/16 :goto_10

    .line 142
    .end local v41    # "hiddenList":Ljava/util/List;
    .restart local v4    # "hiddenList":Ljava/util/List;
    .restart local v7    # "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    .restart local v8    # "isFreeformTask":Z
    .restart local v10    # "t":Landroid/app/ActivityManager$RecentTaskInfo;
    .restart local v11    # "sourceComponent":Landroid/content/ComponentName;
    .restart local v13    # "isStackTask":Z
    .restart local v14    # "info":Landroid/content/pm/ActivityInfo;
    .restart local v16    # "alreadyExist":Z
    .restart local v30    # "isLaunchTarget":Z
    .restart local v39    # "windowingMode":I
    :cond_8
    if-eqz v11, :cond_9

    invoke-virtual {v11}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-interface {v4, v3}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_9

    .line 143
    goto :goto_8

    .line 147
    :cond_9
    iget-boolean v3, v1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$PreloadOptions;->loadTitles:Z

    if-eqz v3, :cond_a

    .line 148
    iget-object v3, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    invoke-virtual {v2, v7, v3}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateActivityTitle(Lcom/android/systemui/shared/recents/model/Task$TaskKey;Landroid/app/ActivityManager$TaskDescription;)Ljava/lang/String;

    move-result-object v3

    goto :goto_9

    .line 149
    :cond_a
    const-string v3, ""

    .line 150
    .local v3, "title":Ljava/lang/String;
    :goto_9
    iget-boolean v6, v1, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan$PreloadOptions;->loadTitles:Z

    if-eqz v6, :cond_b

    .line 151
    iget-object v6, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    invoke-virtual {v2, v7, v6}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateContentDescription(Lcom/android/systemui/shared/recents/model/Task$TaskKey;Landroid/app/ActivityManager$TaskDescription;)Ljava/lang/String;

    move-result-object v6

    .line 152
    :goto_a
    move-object/from16 v27, v6

    goto :goto_b

    :cond_b
    const-string v6, ""

    goto :goto_a

    .line 153
    .local v27, "titleDescription":Ljava/lang/String;
    :goto_b
    if-eqz v13, :cond_c

    .line 154
    iget-object v6, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    const/4 v12, 0x0

    invoke-virtual {v2, v7, v6, v12}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateActivityIcon(Lcom/android/systemui/shared/recents/model/Task$TaskKey;Landroid/app/ActivityManager$TaskDescription;Z)Landroid/graphics/drawable/Drawable;

    move-result-object v6

    .line 155
    :goto_c
    move-object/from16 v24, v6

    goto :goto_d

    :cond_c
    const/4 v12, 0x0

    const/4 v6, 0x0

    goto :goto_c

    .line 156
    .local v24, "icon":Landroid/graphics/drawable/Drawable;
    :goto_d
    invoke-virtual {v2, v7, v12, v12}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getAndUpdateThumbnail(Lcom/android/systemui/shared/recents/model/Task$TaskKey;ZZ)Lcom/android/systemui/shared/recents/model/ThumbnailData;

    move-result-object v6

    .line 158
    .local v6, "thumbnail":Lcom/android/systemui/shared/recents/model/ThumbnailData;
    iget-object v12, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    invoke-virtual {v2, v12}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getActivityPrimaryColor(Landroid/app/ActivityManager$TaskDescription;)I

    move-result v12

    .line 159
    .local v12, "activityColor":I
    iget-object v1, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    invoke-virtual {v2, v1}, Lcom/android/systemui/shared/recents/model/RecentsTaskLoader;->getActivityBackgroundColor(Landroid/app/ActivityManager$TaskDescription;)I

    move-result v1

    .line 160
    .local v1, "backgroundColor":I
    if-eqz v14, :cond_d

    iget-object v2, v14, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v2, v2, Landroid/content/pm/ApplicationInfo;->flags:I

    const/16 v17, 0x1

    and-int/lit8 v2, v2, 0x1

    if-eqz v2, :cond_d

    move/from16 v32, v17

    goto :goto_e

    :cond_d
    const/16 v32, 0x0

    .line 164
    .local v32, "isSystemApp":Z
    :goto_e
    iget-object v2, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mTmpLockedUsers:Landroid/util/SparseBooleanArray;

    move-object/from16 v41, v4

    iget v4, v10, Landroid/app/ActivityManager$RecentTaskInfo;->userId:I

    .end local v4    # "hiddenList":Ljava/util/List;
    .restart local v41    # "hiddenList":Ljava/util/List;
    invoke-virtual {v2, v4}, Landroid/util/SparseBooleanArray;->indexOfKey(I)I

    move-result v2

    if-gez v2, :cond_e

    .line 165
    iget-object v2, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mTmpLockedUsers:Landroid/util/SparseBooleanArray;

    iget v4, v10, Landroid/app/ActivityManager$RecentTaskInfo;->userId:I

    move/from16 v42, v8

    iget-object v8, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mKeyguardManager:Landroid/app/KeyguardManager;

    .end local v8    # "isFreeformTask":Z
    .local v42, "isFreeformTask":Z
    move-object/from16 v43, v11

    iget v11, v10, Landroid/app/ActivityManager$RecentTaskInfo;->userId:I

    .end local v11    # "sourceComponent":Landroid/content/ComponentName;
    .local v43, "sourceComponent":Landroid/content/ComponentName;
    invoke-virtual {v8, v11}, Landroid/app/KeyguardManager;->isDeviceLocked(I)Z

    move-result v8

    invoke-virtual {v2, v4, v8}, Landroid/util/SparseBooleanArray;->put(IZ)V

    goto :goto_f

    .line 167
    .end local v42    # "isFreeformTask":Z
    .end local v43    # "sourceComponent":Landroid/content/ComponentName;
    .restart local v8    # "isFreeformTask":Z
    .restart local v11    # "sourceComponent":Landroid/content/ComponentName;
    :cond_e
    move/from16 v42, v8

    move-object/from16 v43, v11

    .end local v8    # "isFreeformTask":Z
    .end local v11    # "sourceComponent":Landroid/content/ComponentName;
    .restart local v42    # "isFreeformTask":Z
    .restart local v43    # "sourceComponent":Landroid/content/ComponentName;
    :goto_f
    iget-object v2, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mTmpLockedUsers:Landroid/util/SparseBooleanArray;

    iget v4, v10, Landroid/app/ActivityManager$RecentTaskInfo;->userId:I

    invoke-virtual {v2, v4}, Landroid/util/SparseBooleanArray;->get(I)Z

    move-result v2

    .line 169
    .local v2, "isLocked":Z
    iget-object v4, v10, Landroid/app/ActivityManager$RecentTaskInfo;->realActivity:Landroid/content/ComponentName;

    invoke-virtual {v4}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    sget-object v8, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->currentDockActivity:Ljava/lang/String;

    invoke-virtual {v4, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_f

    .line 170
    const-string v4, "XYG"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "addTask equals currentDockActivity -- skip: "

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v11, Lcom/android/systemui/shared/system/ActivityManagerWrapper;->currentDockActivity:Ljava/lang/String;

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v4, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 171
    goto :goto_10

    .line 174
    :cond_f
    new-instance v4, Lcom/android/systemui/shared/recents/model/Task;

    iget-boolean v8, v10, Landroid/app/ActivityManager$RecentTaskInfo;->supportsSplitScreenMultiWindow:Z

    iget-object v11, v10, Landroid/app/ActivityManager$RecentTaskInfo;->taskDescription:Landroid/app/ActivityManager$TaskDescription;

    move-object/from16 v44, v14

    iget v14, v10, Landroid/app/ActivityManager$RecentTaskInfo;->resizeMode:I

    .end local v14    # "info":Landroid/content/pm/ActivityInfo;
    .local v44, "info":Landroid/content/pm/ActivityInfo;
    iget-object v15, v10, Landroid/app/ActivityManager$RecentTaskInfo;->topActivity:Landroid/content/ComponentName;

    move-object/from16 v22, v4

    move-object/from16 v23, v7

    move-object/from16 v25, v6

    move-object/from16 v26, v3

    move/from16 v28, v12

    move/from16 v29, v1

    move/from16 v31, v13

    move/from16 v33, v8

    move-object/from16 v34, v11

    move/from16 v35, v14

    move-object/from16 v36, v15

    move/from16 v37, v2

    invoke-direct/range {v22 .. v37}, Lcom/android/systemui/shared/recents/model/Task;-><init>(Lcom/android/systemui/shared/recents/model/Task$TaskKey;Landroid/graphics/drawable/Drawable;Lcom/android/systemui/shared/recents/model/ThumbnailData;Ljava/lang/String;Ljava/lang/String;IIZZZZLandroid/app/ActivityManager$TaskDescription;ILandroid/content/ComponentName;Z)V

    .line 178
    .local v4, "task":Lcom/android/systemui/shared/recents/model/Task;
    const-string v8, "XYG"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "addTask "

    invoke-virtual {v11, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v14, " / "

    invoke-virtual {v11, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v14, " / "

    invoke-virtual {v11, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v14, v10, Landroid/app/ActivityManager$RecentTaskInfo;->realActivity:Landroid/content/ComponentName;

    invoke-virtual {v14}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v11, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v8, v11}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 179
    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 107
    .end local v1    # "backgroundColor":I
    .end local v2    # "isLocked":Z
    .end local v3    # "title":Ljava/lang/String;
    .end local v4    # "task":Lcom/android/systemui/shared/recents/model/Task;
    .end local v6    # "thumbnail":Lcom/android/systemui/shared/recents/model/ThumbnailData;
    .end local v7    # "taskKey":Lcom/android/systemui/shared/recents/model/Task$TaskKey;
    .end local v10    # "t":Landroid/app/ActivityManager$RecentTaskInfo;
    .end local v12    # "activityColor":I
    .end local v13    # "isStackTask":Z
    .end local v16    # "alreadyExist":Z
    .end local v24    # "icon":Landroid/graphics/drawable/Drawable;
    .end local v27    # "titleDescription":Ljava/lang/String;
    .end local v30    # "isLaunchTarget":Z
    .end local v32    # "isSystemApp":Z
    .end local v39    # "windowingMode":I
    .end local v42    # "isFreeformTask":Z
    .end local v43    # "sourceComponent":Landroid/content/ComponentName;
    .end local v44    # "info":Landroid/content/pm/ActivityInfo;
    :goto_10
    add-int/lit8 v9, v9, 0x1

    move-object/from16 v3, v21

    move/from16 v6, v40

    move-object/from16 v4, v41

    move-object/from16 v1, p1

    move-object/from16 v2, p2

    move/from16 v8, p4

    goto/16 :goto_1

    .line 183
    .end local v9    # "i":I
    .end local v21    # "res":Landroid/content/res/Resources;
    .end local v40    # "taskCount":I
    .end local v41    # "hiddenList":Ljava/util/List;
    .local v3, "res":Landroid/content/res/Resources;
    .local v4, "hiddenList":Ljava/util/List;
    .local v6, "taskCount":I
    :cond_10
    move-object/from16 v21, v3

    move-object/from16 v41, v4

    move/from16 v40, v6

    .end local v3    # "res":Landroid/content/res/Resources;
    .end local v4    # "hiddenList":Ljava/util/List;
    .end local v6    # "taskCount":I
    .restart local v21    # "res":Landroid/content/res/Resources;
    .restart local v40    # "taskCount":I
    .restart local v41    # "hiddenList":Ljava/util/List;
    new-instance v1, Lcom/android/systemui/shared/recents/model/TaskStack;

    invoke-direct {v1}, Lcom/android/systemui/shared/recents/model/TaskStack;-><init>()V

    iput-object v1, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    .line 184
    iget-object v1, v0, Lcom/android/systemui/shared/recents/model/RecentsTaskLoadPlan;->mStack:Lcom/android/systemui/shared/recents/model/TaskStack;

    const/4 v2, 0x0

    invoke-virtual {v1, v5, v2}, Lcom/android/systemui/shared/recents/model/TaskStack;->setTasks(Ljava/util/List;Z)V

    .line 185
    return-void
.end method
