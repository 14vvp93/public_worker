.class public Lcom/hcn/auto/theme/utils/IconThemeUtils;
.super Ljava/lang/Object;
.source "IconThemeUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "com.hcn.auto.theme.utils.IconThemeUtils"

.field private static appInfoList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getCustomAppIcon(Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 5
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "packageName",
            "className"
        }
    .end annotation

    .line 103
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 105
    :cond_0
    sget-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 106
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    :cond_1
    const/4 v0, 0x0

    move v2, v0

    .line 110
    :goto_0
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    if-ge v2, v3, :cond_4

    .line 111
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v3, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 112
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 115
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_2

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_2

    goto :goto_1

    .line 119
    :cond_2
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    .line 121
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object p0

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getBitmap(Ljava/lang/String;Z)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0

    :cond_3
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_4
    return-object v1
.end method

.method public static getCustomAppIconName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "packageName",
            "className"
        }
    .end annotation

    .line 137
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 139
    :cond_0
    sget-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 140
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    :cond_1
    const/4 v0, 0x0

    .line 144
    :goto_0
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v0, v2, :cond_4

    .line 145
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 146
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_3

    .line 149
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_2

    goto :goto_1

    .line 153
    :cond_2
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 154
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object p0

    return-object p0

    :cond_3
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_4
    return-object v1
.end method

.method public static isHidden(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "packageName",
            "className"
        }
    .end annotation

    .line 74
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    return v1

    .line 76
    :cond_0
    sget-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 77
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 81
    :cond_1
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isOverseas()Z

    move-result v0

    if-eqz v0, :cond_2

    const-string v0, "com.hcn.autoupdate"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    return v1

    :cond_2
    const/4 v0, 0x0

    move v1, v0

    .line 86
    :goto_0
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v1, v2, :cond_5

    .line 87
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 88
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 91
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_3

    goto :goto_1

    .line 95
    :cond_3
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$200(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Z

    move-result p0

    return p0

    :cond_4
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_5
    return v0
.end method

.method static isOverseas()Z
    .locals 1

    .line 168
    invoke-static {}, Landroid/Configures/HConfig;->isOverseaVersion()Z

    move-result v0

    return v0
.end method

.method static isTrimEmpty(Ljava/lang/String;)Z
    .locals 0
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "s"
        }
    .end annotation

    if-eqz p0, :cond_1

    .line 164
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result p0

    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method private static parseIconThemeFromXml()Ljava/util/List;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation

    const-string v0, ""

    .line 21
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 24
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object v2

    const-string v3, "icon_theme"

    invoke-virtual {v2, v3}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v2

    .line 26
    :try_start_0
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v3
    :try_end_0
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const/4 v4, 0x0

    move-object v5, v4

    :goto_0
    const/4 v6, 0x1

    if-eq v3, v6, :cond_7

    const/4 v6, 0x2

    const-string v7, "app"

    if-eq v3, v6, :cond_3

    const/4 v6, 0x3

    if-eq v3, v6, :cond_2

    const/4 v6, 0x4

    if-eq v3, v6, :cond_0

    goto/16 :goto_4

    .line 47
    :cond_0
    :try_start_1
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v3

    if-nez v3, :cond_1

    move-object v3, v0

    goto :goto_1

    .line 48
    :cond_1
    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    :goto_1
    invoke-virtual {v5, v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    goto :goto_4

    .line 51
    :cond_2
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_6

    .line 53
    invoke-virtual {v5}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_6

    .line 54
    invoke-interface {v1, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_4

    .line 35
    :cond_3
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_6

    const-string v3, "packageName"

    .line 36
    invoke-interface {v2, v4, v3}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v5, "className"

    .line 37
    invoke-interface {v2, v4, v5}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    const-string v6, "hidden"

    .line 38
    invoke-interface {v2, v4, v6}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 40
    new-instance v7, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    invoke-direct {v7}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;-><init>()V

    if-nez v3, :cond_4

    move-object v3, v0

    goto :goto_2

    .line 41
    :cond_4
    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    :goto_2
    invoke-virtual {v7, v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setPackageName(Ljava/lang/String;)V

    if-nez v5, :cond_5

    move-object v3, v0

    goto :goto_3

    .line 42
    :cond_5
    invoke-virtual {v5}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    :goto_3
    invoke-virtual {v7, v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setClassName(Ljava/lang/String;)V

    const-string v3, "true"

    .line 43
    invoke-virtual {v3, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    invoke-virtual {v7, v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setHidden(Z)V

    move-object v5, v7

    .line 61
    :cond_6
    :goto_4
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v3
    :try_end_1
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_1 .. :try_end_1} :catch_2
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0

    :catch_0
    move-exception v2

    .line 68
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    invoke-static {v3, v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_5

    :catch_1
    move-exception v2

    .line 66
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    invoke-static {v3, v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_5

    :catch_2
    move-exception v2

    .line 64
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    invoke-static {v3, v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_7
    :goto_5
    return-object v1
.end method
