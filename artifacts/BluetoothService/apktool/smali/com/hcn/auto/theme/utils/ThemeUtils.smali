.class public Lcom/hcn/auto/theme/utils/ThemeUtils;
.super Ljava/lang/Object;
.source "ThemeUtils.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "com.hcn.auto.theme.utils.ThemeUtils"

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
    .locals 0

    return-void
.end method

.method private constructor <init>(Landroid/app/Application;)V
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "application"
        }
    .end annotation

    .line 41
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "car_home_style"

    .line 26
    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->CONFIG_CAR_HOME_STYLE:Ljava/lang/String;

    .line 42
    invoke-direct {p0, p1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->loadThemeConfig(Landroid/app/Application;)V

    .line 43
    invoke-direct {p0, p1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->loadTheme(Landroid/app/Application;)V

    return-void
.end method

.method private getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "property"
        }
    .end annotation

    const/4 v0, 0x0

    .line 136
    :try_start_0
    new-instance v1, Ljava/io/File;

    const-string v2, "/apd/xt_base.prop"

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 137
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 138
    new-instance v2, Ljava/io/FileReader;

    invoke-direct {v2, v1}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    move-object v0, v2

    goto :goto_0

    .line 140
    :cond_0
    new-instance v1, Ljava/io/FileReader;

    const-string v2, "/apd/custom.prop"

    invoke-direct {v1, v2}, Ljava/io/FileReader;-><init>(Ljava/lang/String;)V

    move-object v0, v1

    .line 142
    :goto_0
    new-instance v1, Ljava/util/Properties;

    invoke-direct {v1}, Ljava/util/Properties;-><init>()V

    .line 143
    invoke-virtual {v1, v0}, Ljava/util/Properties;->load(Ljava/io/Reader;)V

    .line 144
    invoke-virtual {v1, p1}, Ljava/util/Properties;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 150
    :try_start_1
    invoke-virtual {v0}, Ljava/io/Reader;->close()V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_2

    :catch_0
    move-exception v0

    .line 152
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_2

    :catchall_0
    move-exception p1

    goto :goto_3

    :catch_1
    move-exception p1

    .line 146
    :try_start_2
    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    if-eqz v0, :cond_1

    .line 150
    :try_start_3
    invoke-virtual {v0}, Ljava/io/Reader;->close()V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_2

    goto :goto_1

    :catch_2
    move-exception p1

    .line 152
    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    :cond_1
    :goto_1
    const-string p1, ""

    :goto_2
    return-object p1

    :goto_3
    if-eqz v0, :cond_2

    .line 150
    :try_start_4
    invoke-virtual {v0}, Ljava/io/Reader;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_3

    goto :goto_4

    :catch_3
    move-exception v0

    .line 152
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 155
    :cond_2
    :goto_4
    throw p1
.end method

.method public static getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;
    .locals 1

    .line 47
    sget-object v0, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    return-object v0
.end method

.method public static init(Landroid/app/Application;)Lcom/hcn/auto/theme/utils/ThemeUtils;
    .locals 2
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "application"
        }
    .end annotation

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
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0

    .line 58
    :cond_1
    :goto_0
    sget-object p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->sInstance:Lcom/hcn/auto/theme/utils/ThemeUtils;

    return-object p0
.end method

.method private loadTheme(Landroid/app/Application;)V
    .locals 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "application"
        }
    .end annotation

    .line 82
    invoke-virtual {p1}, Landroid/app/Application;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    .line 83
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    .line 84
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object p1

    .line 87
    :try_start_0
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 89
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "can not find this factory theme package : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 93
    :goto_0
    :try_start_1
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;
    :try_end_1
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    goto :goto_1

    :catch_1
    move-exception v0

    .line 95
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "can not find this factory skin package : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 99
    :goto_1
    :try_start_2
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;
    :try_end_2
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_2 .. :try_end_2} :catch_2

    goto :goto_2

    :catch_2
    move-exception v0

    .line 101
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "can not find this theme package : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 102
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;

    .line 103
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    .line 107
    :goto_2
    :try_start_3
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;
    :try_end_3
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_3 .. :try_end_3} :catch_3

    goto :goto_3

    :catch_3
    move-exception v0

    .line 109
    sget-object v1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "can not find this skin package : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 110
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    .line 111
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    :goto_3
    :try_start_4
    const-string v0, "com.hcn.auto.share"

    .line 117
    invoke-virtual {p1, v0}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Ljava/lang/String;)Landroid/content/res/Resources;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->mShareResources:Landroid/content/res/Resources;
    :try_end_4
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_4 .. :try_end_4} :catch_4

    goto :goto_4

    .line 119
    :catch_4
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string v0, "can not find this share package: com.hcn.auto.share"

    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 122
    :goto_4
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "loadTheme appResources : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", themeResourcesFactory : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", skinResourcesFactory : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 123
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "loadTheme themeResources : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", skinResources : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private loadThemeConfig(Landroid/app/Application;)V
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "application"
        }
    .end annotation

    .line 63
    invoke-virtual {p1}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    const-string p1, "persist.sys.launcher.theme.pkg"

    const-string v0, "com.hcn.auto.theme.md03"

    .line 64
    invoke-static {p1, v0}, Lcom/hcn/auto/theme/utils/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    const-string v1, "persist.sys.launcher.skin.pkg"

    .line 65
    invoke-static {v1, v0}, Lcom/hcn/auto/theme/utils/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    .line 68
    invoke-direct {p0, p1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    const-string v0, ""

    if-eqz p1, :cond_0

    .line 69
    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    .line 70
    :cond_0
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    .line 72
    :cond_1
    invoke-direct {p0, v1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDefaultSystemProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    if-eqz p1, :cond_2

    .line 73
    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    .line 74
    :cond_2
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    iput-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    .line 77
    :cond_3
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "current theme config : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", skin config : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 78
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "factory theme config : "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method


# virtual methods
.method public getAppBackground()Landroid/graphics/drawable/Drawable;
    .locals 4

    .line 432
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getShareResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 433
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->mShareResources:Landroid/content/res/Resources;

    const-string v1, "background"

    const-string v2, "drawable"

    const-string v3, "com.hcn.auto.share"

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 437
    iget-object v1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->mShareResources:Landroid/content/res/Resources;

    const/4 v2, 0x0

    invoke-virtual {v1, v0, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    if-eqz v0, :cond_0

    return-object v0

    .line 444
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getAppWallpaper()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method public getAppWallpaper()Landroid/graphics/drawable/Drawable;
    .locals 3
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .line 418
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->context:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "car_home_style"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v1, v0, :cond_0

    const-string v0, "headrest_wallpaper"

    .line 419
    invoke-virtual {p0, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0

    :cond_0
    const-string v0, "app_wallpaper"

    .line 421
    invoke-virtual {p0, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method public getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 189
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getBitmap(Ljava/lang/String;Z)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1
.end method

.method public getBitmap(Ljava/lang/String;Z)Landroid/graphics/Bitmap;
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 161
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "drawable"

    if-eqz v0, :cond_0

    .line 162
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 164
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-static {p1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1

    :cond_0
    if-eqz p2, :cond_1

    .line 169
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 170
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 172
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-static {p1, p2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1

    .line 177
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 178
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 180
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-static {p2, p1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    return-object p1

    .line 183
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getBitmap return null"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    const/4 p1, 0x0

    return-object p1
.end method

.method public getColor(Ljava/lang/String;)I
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 292
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getColor(Ljava/lang/String;Z)I

    move-result p1

    return p1
.end method

.method public getColor(Ljava/lang/String;Z)I
    .locals 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 262
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const/4 v1, 0x0

    const-string v2, "color"

    if-eqz v0, :cond_0

    .line 263
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 265
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0, v1}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    return p1

    :cond_0
    if-eqz p2, :cond_1

    .line 270
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 271
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v2, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 273
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {p1, p2, v1}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    return p1

    .line 279
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 280
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v2, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 282
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {p2, p1, v1}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    return p1

    .line 285
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getColor return 0"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    const/4 p1, 0x0

    return p1
.end method

.method public getDrawable(Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 222
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getDrawable(Ljava/lang/String;Z)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1
.end method

.method public getDrawable(Ljava/lang/String;Z)Landroid/graphics/drawable/Drawable;
    .locals 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 194
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "drawable"

    const/4 v2, 0x0

    if-eqz v0, :cond_0

    .line 195
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v3, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v1, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 197
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1

    :cond_0
    if-eqz p2, :cond_1

    .line 202
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 203
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 205
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {p1, p2, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1

    .line 210
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 211
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 213
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {p2, p1, v2}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1

    .line 216
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getDrawable return null"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    return-object v2
.end method

.method public getInteger(Ljava/lang/String;)I
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 359
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInteger(Ljava/lang/String;Z)I

    move-result p1

    return p1
.end method

.method public getInteger(Ljava/lang/String;Z)I
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 331
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "integer"

    if-eqz v0, :cond_0

    .line 332
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 334
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1

    return p1

    :cond_0
    if-eqz p2, :cond_1

    .line 339
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 340
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 342
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1

    return p1

    .line 347
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 348
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 350
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {p2, p1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result p1

    return p1

    .line 353
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getInteger return 0"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    const/4 p1, 0x0

    return p1
.end method

.method public getShareResources()Landroid/content/res/Resources;
    .locals 1

    .line 387
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->mShareResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getSkinPackageName()Ljava/lang/String;
    .locals 1

    .line 367
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    return-object v0
.end method

.method public getSkinPackageNameFactory()Ljava/lang/String;
    .locals 1

    .line 375
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    return-object v0
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

.method public getString(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 326
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getString(Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public getString(Ljava/lang/String;Z)Ljava/lang/String;
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 297
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "string"

    if-eqz v0, :cond_0

    .line 298
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 300
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :cond_0
    if-eqz p2, :cond_1

    .line 305
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 306
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 308
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    .line 313
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 314
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 316
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {p2, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    .line 319
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getString return null"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    const/4 p1, 0x0

    return-object p1
.end method

.method public getThemePackageName()Ljava/lang/String;
    .locals 1

    .line 363
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageName:Ljava/lang/String;

    return-object v0
.end method

.method public getThemePackageNameFactory()Ljava/lang/String;
    .locals 1

    .line 371
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themePackageNameFactory:Ljava/lang/String;

    return-object v0
.end method

.method public getThemeResources()Landroid/content/res/Resources;
    .locals 1

    .line 379
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getThemeResourcesFactory()Landroid/content/res/Resources;
    .locals 1

    .line 398
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->themeResourcesFactory:Landroid/content/res/Resources;

    return-object v0
.end method

.method public getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;
    .locals 1
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "name"
        }
    .end annotation

    const/4 v0, 0x1

    .line 257
    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getXml(Ljava/lang/String;Z)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1
.end method

.method public getXml(Ljava/lang/String;Z)Landroid/content/res/XmlResourceParser;
    .locals 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "name",
            "needFromFactory"
        }
    .end annotation

    .line 227
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "xml"

    if-eqz v0, :cond_0

    .line 228
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageName:Ljava/lang/String;

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_0

    .line 230
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResources:Landroid/content/res/Resources;

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1

    :cond_0
    if-eqz p2, :cond_1

    .line 235
    invoke-virtual {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getSkinResourcesFactory()Landroid/content/res/Resources;

    move-result-object p2

    if-eqz p2, :cond_1

    .line 236
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinPackageNameFactory:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p2

    if-eqz p2, :cond_1

    .line 238
    iget-object p1, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->skinResourcesFactory:Landroid/content/res/Resources;

    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1

    .line 244
    :cond_1
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    if-eqz p2, :cond_2

    .line 245
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appPackageName:Ljava/lang/String;

    invoke-virtual {p2, p1, v1, v0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_3

    .line 247
    iget-object p2, p0, Lcom/hcn/auto/theme/utils/ThemeUtils;->appResources:Landroid/content/res/Resources;

    invoke-virtual {p2, p1}, Landroid/content/res/Resources;->getXml(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    return-object p1

    .line 250
    :cond_2
    sget-object p1, Lcom/hcn/auto/theme/utils/ThemeUtils;->TAG:Ljava/lang/String;

    const-string p2, "Theme error : appResources = null, getXml return null"

    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_3
    const/4 p1, 0x0

    return-object p1
.end method
