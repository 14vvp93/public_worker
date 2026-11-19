.class public Lcom/hcn/auto/theme/utils/ThemeUtils;
.super Ljava/lang/Object;
.source "ThemeUtils.java"


# static fields
.field private static final TAG:Ljava/lang/String;

.field private static volatile sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;


# instance fields
.field private final CONFIG_CAR_HOME_STYLE:Ljava/lang/String;

.field private appPackageName:Ljava/lang/String;

.field private appResources:Landroid/content/res/Resources;

.field private context:Landroid/content/Context;

.field private mShareResources:Landroid/content/res/Resources;

.field private skinPackageName:Ljava/lang/String;

.field private skinPackageNameFactory:Ljava/lang/String;

.field private skinResources:Landroid/content/res/Resources;

.field private skinResourcesFactory:Landroid/content/res/Resources;

.field private themePackageName:Ljava/lang/String;

.field private themePackageNameFactory:Ljava/lang/String;

.field private themeResources:Landroid/content/res/Resources;

.field private themeResourcesFactory:Landroid/content/res/Resources;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 24
    const-class v0, Lcom/hcn/auto/theme/utils/ThemeUtils;

    invoke-virtual {v0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    return-void
.end method

.method private constructor <init>(Landroid/app/Application;)V
    .locals 1
    .param p1, "application"    # Landroid/app/Application;

    .line 41
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 26
    const-string v0, "car_home_style"

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->CONFIG_CAR_HOME_STYLE:Ljava/lang/String;

    .line 42
    invoke-direct {p0, p1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->loadThemeConfig(Landroid/app/Application;)V

    .line 43
    invoke-direct {p0, p1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->loadTheme(Landroid/app/Application;)V

    .line 44
    return-void
.end method

.method private getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;
    .locals 5
    .param p1, "property"    # Ljava/lang/String;

    .line 133
    const-string v0, ""

    .line 134
    .local v0, "value":Ljava/lang/String;
    const/4 v1, 0x0

    .line 136
    .local v1, "reader":Ljava/io/Reader;
    :try_start_0
    new-instance v2, Ljava/io/File;

    const-string v3, "/apd/xt_base.prop"

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 137
    .local v2, "file":Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    if-eqz v3, :cond_0

    .line 138
    new-instance v3, Ljava/io/FileReader;

    invoke-direct {v3, v2}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    move-object v1, v3

    goto :goto_0

    .line 140
    :cond_0
    new-instance v3, Ljava/io/FileReader;

    const-string v4, "/apd/custom.prop"

    invoke-direct {v3, v4}, Ljava/io/FileReader;-><init>(Ljava/lang/String;)V

    move-object v1, v3

    .line 142
    :goto_0
    new-instance v3, Ljava/util/Properties;

    invoke-direct {v3}, Ljava/util/Properties;-><init>()V

    .line 143
    .local v3, "pro":Ljava/util/Properties;
    invoke-virtual {v3, v1}, Ljava/util/Properties;->load(Ljava/io/Reader;)V

    .line 144
    invoke-virtual {v3, p1}, Ljava/util/Properties;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    move-object v0, v4

    .line 148
    .end local v2    # "file":Ljava/io/File;
    .end local v3    # "pro":Ljava/util/Properties;
    nop

    .line 150
    :try_start_1
    invoke-virtual {v1}, Ljava/io/Reader;->close()V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    .line 153
    :goto_1
    goto :goto_2

    .line 151
    :catch_0
    move-exception v2

    .line 152
    .local v2, "e":Ljava/io/IOException;
    invoke-virtual {v2}, Ljava/io/IOException;->printStackTrace()V

    .end local v2    # "e":Ljava/io/IOException;
    goto :goto_1

    .line 148
    :catchall_0
    move-exception v2

    goto :goto_3

    .line 145
    :catch_1
    move-exception v2

    .line 146
    .restart local v2    # "e":Ljava/io/IOException;
    :try_start_2
    invoke-virtual {v2}, Ljava/io/IOException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 148
    .end local v2    # "e":Ljava/io/IOException;
    if-eqz v1, :cond_1

    .line 150
    :try_start_3
    invoke-virtual {v1}, Ljava/io/Reader;->close()V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_0

    goto :goto_1

    .line 156
    :cond_1
    :goto_2
    return-object v0

    .line 148
    :goto_3
    if-eqz v1, :cond_2

    .line 150
    :try_start_4
    invoke-virtual {v1}, Ljava/io/Reader;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_2

    .line 153
    goto :goto_4

    .line 151
    :catch_2
    move-exception v3

    .line 152
    .local v3, "e":Ljava/io/IOException;
    invoke-virtual {v3}, Ljava/io/IOException;->printStackTrace()V

    .line 153
    .end local v3    # "e":Ljava/io/IOException;
    :cond_2
    :goto_4
    throw v2
.end method

.method public static getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;
    .locals 1

    .line 47
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    return-object v0
.end method

.method public static init(Landroid/app/Application;)Lcom/hcn/auto/theme/utils/ThemeUtils;
    .locals 2
    .param p0, "application"    # Landroid/app/Application;

    .line 51
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    if-nez v0, :cond_1

    .line 52
    const-class v0, Lcom/hcn/auto/theme/utils/ThemeUtils;

    monitor-enter v0

    .line 53
    :try_start_0
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    if-nez v1, :cond_0

    .line 54
    new-instance v1, Lcom/hcn/auto/theme/utils/ThemeUtils;

    invoke-direct {v1, p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;-><init>(Landroid/app/Application;)V

    sput-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    .line 56
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    .line 58
    :cond_1
    :goto_0
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    return-object v0
.end method

.method private loadTheme(Landroid/app/Application;)V
    .locals 5
    .param p1, "application"    # Landroid/app/Application;

    .line 82
    invoke-virtual {p1}, Landroid/app/Application;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    .line 83
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    .line 84
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    .line 87
    .local v0, "pm":Landroid/content/pm/PackageManager;
    :try_start_0
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 90
    goto :goto_0

    .line 88
    :catch_0
    move-exception v1

    .line 89
    .local v1, "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    sget-object v2, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "can not find this factory theme package : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 93
    .end local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    :goto_0
    :try_start_1
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;
    :try_end_1
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    .line 96
    goto :goto_1

    .line 94
    :catch_1
    move-exception v1

    .line 95
    .restart local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    sget-object v2, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "can not find this factory skin package : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 99
    .end local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    :goto_1
    :try_start_2
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;
    :try_end_2
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_2 .. :try_end_2} :catch_2

    .line 104
    goto :goto_2

    .line 100
    :catch_2
    move-exception v1

    .line 101
    .restart local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    sget-object v2, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "can not find this theme package : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 102
    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;

    iput-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;

    .line 103
    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    iput-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    .line 107
    .end local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    :goto_2
    :try_start_3
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;
    :try_end_3
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_3 .. :try_end_3} :catch_3

    .line 112
    goto :goto_3

    .line 108
    :catch_3
    move-exception v1

    .line 109
    .restart local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    sget-object v2, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "can not find this skin package : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 110
    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iput-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    .line 111
    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    iput-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    .line 117
    .end local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    :goto_3
    :try_start_4
    const-string v1, "com.hcn.auto.share"

    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->mShareResources:Landroid/content/res/Resources;
    :try_end_4
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_4 .. :try_end_4} :catch_4

    .line 120
    goto :goto_4

    .line 118
    :catch_4
    move-exception v1

    .line 119
    .restart local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    sget-object v2, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v3, "can not find this share package: com.hcn.auto.share"

    invoke-static {v2, v3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 122
    .end local v1    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    :goto_4
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "loadTheme appResources : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v3, ", themeResourcesFactory : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v3, ", skinResourcesFactory : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 123
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "loadTheme themeResources : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v3, ", skinResources : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 124
    return-void
.end method

.method private loadThemeConfig(Landroid/app/Application;)V
    .locals 3
    .param p1, "application"    # Landroid/app/Application;

    .line 63
    invoke-virtual {p1}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    .line 64
    const-string v0, "persist.sys.launcher.theme.pkg"

    const-string v1, "com.hcn.auto.theme.md03"

    invoke-static {v0, v1}, Lcom/hcn/auto/theme/utils/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    .line 65
    const-string v0, "persist.sys.launcher.skin.pkg"

    const-string v1, "com.hcn.auto.theme.md03"

    invoke-static {v0, v1}, Lcom/hcn/auto/theme/utils/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    .line 68
    const-string v0, "persist.sys.launcher.theme.pkg"

    invoke-direct {p0, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    .line 69
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    if-eqz v0, :cond_0

    const-string v0, ""

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 70
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    .line 72
    :cond_1
    const-string v0, "persist.sys.launcher.skin.pkg"

    invoke-direct {p0, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    .line 73
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    if-eqz v0, :cond_2

    const-string v0, ""

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    .line 74
    :cond_2
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    .line 77
    :cond_3
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "current theme config : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ", skin config : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 78
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "factory theme config : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ", skin config : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 79
    return-void
.end method


# virtual methods
.method public getBitmap(Ljava/lang/String;Z)Landroid/graphics/Bitmap;
    .locals 4
    .param p1, "name"    # Ljava/lang/String;
    .param p2, "needFromFactory"    # Z

    .line 160
    const/4 v0, 0x0

    .line 161
    .local v0, "identifier":I
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 162
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    const-string v2, "drawable"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 163
    if-eqz v0, :cond_0

    .line 164
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-static {v1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 168
    :cond_0
    if-eqz p2, :cond_1

    .line 169
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object v1

    if-eqz v1, :cond_1

    .line 170
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    const-string v2, "drawable"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 171
    if-eqz v0, :cond_1

    .line 172
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-static {v1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 177
    :cond_1
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_2

    .line 178
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    const-string v2, "drawable"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 179
    if-eqz v0, :cond_3

    .line 180
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-static {v1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 183
    :cond_2
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v2, "Theme error : appResources = null, getBitmap return null"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 185
    :cond_3
    const/4 v1, 0x0

    return-object v1
.end method

.method public getSkinResources()Landroid/content/res/Resources;
    .locals 2

    .line 391
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    if-nez v0, :cond_0

    .line 392
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v1, "Theme error : skinResources = null"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 394
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getSkinResourcesFactory()Landroid/content/res/Resources;
    .locals 2

    .line 402
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    if-nez v0, :cond_0

    .line 403
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v1, "Theme error : skinResourcesFactory = null"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 405
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;
    .locals 1
    .param p1, "name"    # Ljava/lang/String;

    .line 257
    const/4 v0, 0x1

    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getXml(Ljava/lang/String;Z)Landroid/content/res/XmlResourceParser;

    move-result-object v0

    return-object v0
.end method

.method public getXml(Ljava/lang/String;Z)Landroid/content/res/XmlResourceParser;
    .locals 4
    .param p1, "name"    # Ljava/lang/String;
    .param p2, "needFromFactory"    # Z

    .line 226
    const/4 v0, 0x0

    .line 227
    .local v0, "identifier":I
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 228
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    const-string/jumbo v2, "xml"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 229
    if-eqz v0, :cond_0

    .line 230
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object v1

    return-object v1

    .line 234
    :cond_0
    if-eqz p2, :cond_1

    .line 235
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object v1

    if-eqz v1, :cond_1

    .line 236
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    const-string/jumbo v2, "xml"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 237
    if-eqz v0, :cond_1

    .line 238
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object v1

    return-object v1

    .line 244
    :cond_1
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz v1, :cond_2

    .line 245
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    const-string/jumbo v2, "xml"

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 246
    if-eqz v0, :cond_3

    .line 247
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object v1

    return-object v1

    .line 250
    :cond_2
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v2, "Theme error : appResources = null, getXml return null"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 253
    :cond_3
    const/4 v1, 0x0

    return-object v1
.end method
