.class public Lcom/hcn/auto/systemui/SkinManager;
.super Ljava/lang/Object;
.source "SkinManager.java"


# static fields
.field private static volatile sInstance:Lcom/hcn/auto/systemui/SkinManager;


# instance fields
.field private mContext:Landroid/content/Context;

.field private mSkinPath:Ljava/lang/String;


# direct methods
.method private constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 37
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 22
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/auto/systemui/SkinManager;->mContext:Landroid/content/Context;

    .line 24
    iput-object v0, p0, Lcom/hcn/auto/systemui/SkinManager;->mSkinPath:Ljava/lang/String;

    .line 38
    iput-object p1, p0, Lcom/hcn/auto/systemui/SkinManager;->mContext:Landroid/content/Context;

    .line 39
    invoke-static {p1}, Lcom/hcn/skin/support/SkinCompatManager;->create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 40
    .local v0, "manager":Lcom/hcn/skin/support/SkinCompatManager;
    return-void
.end method

.method public static getInstance(Landroid/content/Context;)Lcom/hcn/auto/systemui/SkinManager;
    .locals 2
    .param p0, "context"    # Landroid/content/Context;

    .line 27
    sget-object v0, Lcom/hcn/auto/systemui/SkinManager;->sInstance:Lcom/hcn/auto/systemui/SkinManager;

    if-nez v0, :cond_1

    .line 28
    const-class v0, Lcom/hcn/auto/systemui/SkinManager;

    monitor-enter v0

    .line 29
    :try_start_0
    sget-object v1, Lcom/hcn/auto/systemui/SkinManager;->sInstance:Lcom/hcn/auto/systemui/SkinManager;

    if-nez v1, :cond_0

    .line 30
    new-instance v1, Lcom/hcn/auto/systemui/SkinManager;

    invoke-direct {v1, p0}, Lcom/hcn/auto/systemui/SkinManager;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/hcn/auto/systemui/SkinManager;->sInstance:Lcom/hcn/auto/systemui/SkinManager;

    .line 32
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    .line 34
    :cond_1
    :goto_0
    sget-object v0, Lcom/hcn/auto/systemui/SkinManager;->sInstance:Lcom/hcn/auto/systemui/SkinManager;

    return-object v0
.end method


# virtual methods
.method public getApkPathByPackageName(Ljava/lang/String;)Ljava/lang/String;
    .locals 7
    .param p1, "pkg"    # Ljava/lang/String;

    .line 154
    const/4 v0, 0x0

    :try_start_0
    iget-object v1, p0, Lcom/hcn/auto/systemui/SkinManager;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    .line 155
    .local v1, "pm":Landroid/content/pm/PackageManager;
    const/4 v2, 0x0

    invoke-virtual {v1, p1, v2}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v2

    .line 156
    .local v2, "info":Landroid/content/pm/ApplicationInfo;
    if-nez v2, :cond_0

    .line 157
    return-object v0

    .line 159
    :cond_0
    iget-object v3, v2, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 160
    .local v3, "apkPath":Ljava/lang/String;
    iput-object v3, p0, Lcom/hcn/auto/systemui/SkinManager;->mSkinPath:Ljava/lang/String;

    .line 161
    const-string v4, "SkinManager"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "getApkPathByPackageName apkPath:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " pkg:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 162
    return-object v3

    .line 164
    .end local v1    # "pm":Landroid/content/pm/PackageManager;
    .end local v2    # "info":Landroid/content/pm/ApplicationInfo;
    .end local v3    # "apkPath":Ljava/lang/String;
    :catch_0
    move-exception v1

    .line 165
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "SkinManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Didn\'t find package "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 167
    .end local v1    # "e":Ljava/lang/Exception;
    return-object v0
.end method

.method public getApkPathBySkinName(Ljava/lang/String;)Ljava/lang/String;
    .locals 16
    .param p1, "skinName"    # Ljava/lang/String;

    .line 108
    move-object/from16 v0, p1

    invoke-static/range {p1 .. p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_5

    const-string v1, ""

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_5

    .line 109
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 111
    .local v1, "sb":Ljava/lang/StringBuilder;
    const/4 v2, 0x4

    new-array v2, v2, [Ljava/lang/String;

    const-string v3, "system/app"

    const/4 v4, 0x0

    aput-object v3, v2, v4

    const-string v3, "data/config/app"

    const/4 v5, 0x1

    aput-object v3, v2, v5

    const/4 v3, 0x2

    const-string v5, "apd/app"

    aput-object v5, v2, v3

    const/4 v3, 0x3

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v6

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, "/skin"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    aput-object v5, v2, v3

    .line 112
    .local v2, "searchPaths":[Ljava/lang/String;
    const/4 v3, 0x0

    .line 114
    .local v3, "skinApkPath":Ljava/lang/String;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "SystemUITheme_"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v5

    .line 116
    .local v5, "fixSkinName":Ljava/lang/String;
    move v6, v4

    .local v6, "i":I
    :goto_0
    array-length v7, v2

    if-ge v6, v7, :cond_4

    .line 117
    aget-object v7, v2, v6

    .line 118
    .local v7, "path":Ljava/lang/String;
    const-string v8, "SkinManager"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "----path:"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 119
    const/4 v8, 0x0

    .line 120
    .local v8, "apkPath":Ljava/lang/String;
    new-instance v9, Ljava/io/File;

    invoke-direct {v9, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 121
    .local v9, "apkDir":Ljava/io/File;
    invoke-virtual {v9}, Ljava/io/File;->exists()Z

    move-result v10

    if-eqz v10, :cond_3

    invoke-virtual {v9}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-eqz v10, :cond_3

    .line 122
    invoke-virtual {v9}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object v10

    .line 123
    .local v10, "list":[Ljava/lang/String;
    move v11, v4

    .local v11, "index":I
    :goto_1
    array-length v12, v10

    if-ge v11, v12, :cond_2

    .line 124
    aget-object v12, v10, v11

    .line 126
    .local v12, "name":Ljava/lang/String;
    invoke-virtual {v12}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v13

    invoke-virtual {v13, v5}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v13

    if-eqz v13, :cond_1

    .line 127
    const-string v13, ".apk"

    invoke-virtual {v12, v13}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v13

    if-eqz v13, :cond_0

    .line 128
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v14, "/"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v13, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    goto :goto_2

    .line 130
    :cond_0
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v14, "/"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v13, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v14, "/"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v13, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v14, ".apk"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 132
    :goto_2
    const-string v13, "SkinManager"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "getApkPathBySkinName "

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v14, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v15, " path:"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v14, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 133
    goto :goto_3

    .line 123
    .end local v12    # "name":Ljava/lang/String;
    :cond_1
    add-int/lit8 v11, v11, 0x1

    goto :goto_1

    .line 136
    .end local v11    # "index":I
    :cond_2
    :goto_3
    invoke-static {v8}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v11

    if-nez v11, :cond_3

    .line 137
    new-instance v11, Ljava/io/File;

    invoke-direct {v11, v8}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 138
    .local v11, "apk":Ljava/io/File;
    invoke-virtual {v11}, Ljava/io/File;->exists()Z

    move-result v12

    if-eqz v12, :cond_3

    .line 139
    invoke-virtual {v11}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    .line 140
    move-object/from16 v12, p0

    iput-object v3, v12, Lcom/hcn/auto/systemui/SkinManager;->mSkinPath:Ljava/lang/String;

    .line 141
    goto :goto_4

    .line 116
    .end local v7    # "path":Ljava/lang/String;
    .end local v8    # "apkPath":Ljava/lang/String;
    .end local v9    # "apkDir":Ljava/io/File;
    .end local v10    # "list":[Ljava/lang/String;
    .end local v11    # "apk":Ljava/io/File;
    :cond_3
    move-object/from16 v12, p0

    add-int/lit8 v6, v6, 0x1

    goto/16 :goto_0

    .line 147
    .end local v6    # "i":I
    :cond_4
    move-object/from16 v12, p0

    :goto_4
    return-object v3

    .line 149
    .end local v1    # "sb":Ljava/lang/StringBuilder;
    .end local v2    # "searchPaths":[Ljava/lang/String;
    .end local v3    # "skinApkPath":Ljava/lang/String;
    .end local v5    # "fixSkinName":Ljava/lang/String;
    :cond_5
    move-object/from16 v12, p0

    const/4 v1, 0x0

    return-object v1
.end method

.method public loadSkin()V
    .locals 6

    .line 56
    const/4 v0, 0x0

    .line 57
    .local v0, "skinName":Ljava/lang/String;
    const-string v1, "persist.sys.systemui.skin"

    const-string v2, ""

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 58
    .local v1, "configSkin":Ljava/lang/String;
    const-string v2, "SkinManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "loadSkin:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 59
    const-string v2, ""

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 60
    move-object v0, v1

    goto :goto_0

    .line 62
    :cond_0
    const-string v2, "persist.sys.skin"

    const-string v3, ""

    invoke-static {v2, v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 63
    .local v2, "launcherSkin":Ljava/lang/String;
    const-string v3, "SkinManager"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "load launcherSkin:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 64
    const-string v3, ""

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_1

    .line 65
    move-object v0, v2

    .line 68
    .end local v2    # "launcherSkin":Ljava/lang/String;
    :cond_1
    :goto_0
    if-eqz v0, :cond_3

    .line 69
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "com.hcn.systemui.theme."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/SkinManager;->getApkPathByPackageName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 70
    .local v2, "skinApkPath":Ljava/lang/String;
    if-nez v2, :cond_2

    .line 71
    invoke-virtual {p0, v0}, Lcom/hcn/auto/systemui/SkinManager;->getApkPathBySkinName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 73
    :cond_2
    if-eqz v2, :cond_3

    .line 74
    invoke-virtual {p0, v2}, Lcom/hcn/auto/systemui/SkinManager;->loadSkinByPath(Ljava/lang/String;)Z

    .line 77
    .end local v2    # "skinApkPath":Ljava/lang/String;
    :cond_3
    return-void
.end method

.method public loadSkinByPath(Ljava/lang/String;)Z
    .locals 3
    .param p1, "skinApkPath"    # Ljava/lang/String;

    .line 86
    const-string v0, "SkinManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "loadSkinByPath "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 87
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 88
    return v1

    .line 90
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinByPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 91
    .local v0, "getPath":Ljava/lang/String;
    if-eqz v0, :cond_1

    const/4 v1, 0x1

    nop

    :cond_1
    return v1
.end method
